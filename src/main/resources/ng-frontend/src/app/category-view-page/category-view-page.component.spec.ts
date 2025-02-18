import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CategoryViewPageComponent } from './category-view-page.component';

describe('CategoryViewPageComponent', () => {
  let component: CategoryViewPageComponent;
  let fixture: ComponentFixture<CategoryViewPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CategoryViewPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CategoryViewPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
