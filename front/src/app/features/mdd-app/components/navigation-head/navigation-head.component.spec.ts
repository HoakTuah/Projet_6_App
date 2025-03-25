import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavigationHeadComponent } from './navigation-head.component';

describe('NavigationHeadComponent', () => {
  let component: NavigationHeadComponent;
  let fixture: ComponentFixture<NavigationHeadComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavigationHeadComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NavigationHeadComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
