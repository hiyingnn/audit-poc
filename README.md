# Context

1. Shadow is a historical version of a domain object restored from a snapshot:
    - Different scopes:
      a. Shallow — the defult scope — Shadows are created only from snapshots selected directly in the main JQL query.
      b. Child-value-object — JaVers loads all child Value Objects owned by selected Entities. Since 3.7.5, this scope
      is implicitly enabled for all Shadow queries and can’t be disabled.
      c. Commit-deep — Shadows are created from all snapshots saved in commits touched by the main query.
      d. Deep+ — JaVers tries to restore full object graphs with (possibly) all objects loaded.
2. Change represents an atomic difference between two objects.
3. Snapshot is a historical state of a domain object captured as the property:value map.

## POC - Phase 1 (Simple use-case)

1. "find all versions given id": `api/v1/audit/shadows/{objectId}?page={page}&size={size}`
    - returns reconstructed (paginated) CareerHistory object
    - Support for pagination: skip and limit in JQL
    - Limitations: No way of ordering by property/chronological order (default is reverse-chronological)
    - Corner cases for distributed systems: must used random CommitId generator (via CommitId generator algorithm)
2. "list specific version": `api/v1/audit/shadows/{objectId}/version/1`
    - Note that Javers version is 1-indexed, whereas Mongo version is 0-indexed: we should try to keep it consistent
3. "changes given id": `/api/v1/audit/changes/{objectId}?property={property}&isValueObject={isValueObject}`
    - Using the default ValueObject for all nested entities (default setting)
    - To handle element shift: use Levenshtein algorithm, which calculates short and clear change list even in case when
      elements are shifted. It doesn’t care about index changes for shifted elements.
    - "filter by property changes":
        - if value: use `withPropertyChange` filter
        - if valueObject, pass in path of object subtree and use `byValueObjectId` in JQL

## Evaluation

1. Complexity of Javers Objects e.g. `Changes`:
    - Example would be the return of `Changes` API: contains metadata like `changeType`, `propertyNameWithType`. `left`
      + `right` values
    - Need to abstract out such information, since hard for clients to interpret
    - A bit too early to define the contract, since it is dependent on the requirements of the UI. But `changes` would
      most likely be something like this
        - Need to have reconstructed domain object
        - Need to have easily interpretable changelog
   ``` java
       Class CompleteHistory {
           List<Change> changes;
       }
       
       Class Change {
           AuditSnapshot snapshotVer0; // earlier snapshot
           AuditSnapshot snapshotshotVer1; // later snapshot
           Map<String, String> changelog; // where key is path of object (easily interpretable by client), value is changelog (something similart to the change.prettyPrint(), the diff between fields of snapshots) 
       }
       
       Class AuditSnapshot<T> {
           T snapshot;
           Long version; // may not be necessary since within snapshot
       }
       
   ```
2. Limitation of ordering/filtering snapshots/shadows:
    - Based on how Javers stores its snapshots for optimal retrieval, most likely will not have additional
      ordering/filters
    - This means that our common use-case of "all changes by a profile - filter by profileId" will be hard to fulfil
    - Some workarounds:
        - have a "container" entity Mega object which houses all child entities
        - Using commitProperty filter
          - `QueryBuilder.byClass(CareerHistory.class).withCommitProperty("profileId","213");`
            - But this may be an optimal solution since it has to load all instances of the entity and then do a filter
              in-mem. 
