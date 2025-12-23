export interface Category {
  nr: string,
  name: string,
  slug: string,
  children: Category[],
}
