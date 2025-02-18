import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PageableComponent } from './pageable.component';

describe('PageableComponent', () => {
  let component: PageableComponent;
  let fixture: ComponentFixture<PageableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PageableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PageableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
