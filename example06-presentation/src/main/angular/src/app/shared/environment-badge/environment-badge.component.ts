import { Component } from '@angular/core';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'wt2-environment-badge',
  templateUrl: './environment-badge.component.html',
  styleUrls: ['./environment-badge.component.sass']
})
export class EnvironmentBadgeComponent {

  public get isProduction(): boolean {
    return environment.production;
  }
}
