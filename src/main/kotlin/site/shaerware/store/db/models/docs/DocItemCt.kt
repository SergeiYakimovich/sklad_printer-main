package site.shaerware.store.db.models.docs

interface DocItemCt<T: DocEntity> {
    var owner: T;
    var orderNo: Int
}