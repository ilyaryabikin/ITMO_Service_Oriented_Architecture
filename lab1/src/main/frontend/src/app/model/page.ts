export interface Page<Type> {
  pageNumber: number,
  pageSize: number,
  totalCount: number,
  lastPage: number,
  result: Type[]
}
