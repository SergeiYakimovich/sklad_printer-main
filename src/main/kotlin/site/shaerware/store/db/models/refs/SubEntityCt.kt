package site.shaerware.store.db.models.refs

interface SubEntityCt<T : RefEntity> {
    var owner: T
}
