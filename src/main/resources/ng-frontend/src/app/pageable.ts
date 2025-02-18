export interface Pageable {

  offset: number,
  pageNumber: number,
  pageSize: number,
  paged: boolean,
  sort: {
    sorted: boolean,
  }
}

export class PageableDefault implements Pageable {
  offset= 0;
  pageNumber=  0;
  pageSize=  10;
  paged = false;
  sort = {
    sorted: false
  }

}
