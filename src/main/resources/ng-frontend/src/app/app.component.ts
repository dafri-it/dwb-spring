import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {CategoryIndexPageComponent} from './category-index-page/category-index-page.component';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ng-frontend';
}
