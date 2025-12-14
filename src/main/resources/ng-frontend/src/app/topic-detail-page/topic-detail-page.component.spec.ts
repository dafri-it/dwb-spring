import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TopicDetailPageComponent } from './topic-detail-page.component';

describe('TopicDetailPageComponent', () => {
  let component: TopicDetailPageComponent;
  let fixture: ComponentFixture<TopicDetailPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TopicDetailPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TopicDetailPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
