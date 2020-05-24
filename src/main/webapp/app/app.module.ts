import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import './vendor';
import { AhomeSharedModule } from 'app/shared/shared.module';
import { AhomeCoreModule } from 'app/core/core.module';
import { AhomeAppRoutingModule } from './app-routing.module';
import { AhomeHomeModule } from './home/home.module';
import { AhomeEntityModule } from './entities/entity.module';
// jhipster-needle-angular-add-module-import JHipster will add new module here
import { MainComponent } from './layouts/main/main.component';
import { NavbarComponent } from './layouts/navbar/navbar.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { PageRibbonComponent } from './layouts/profiles/page-ribbon.component';
import { ActiveMenuDirective } from './layouts/navbar/active-menu.directive';
import { ErrorComponent } from './layouts/error/error.component';

@NgModule({
  imports: [
    BrowserModule,
    AhomeSharedModule,
    AhomeCoreModule,
    AhomeHomeModule,
    // jhipster-needle-angular-add-module JHipster will add new module here
    AhomeEntityModule,
    AhomeAppRoutingModule,
  ],
  declarations: [MainComponent, NavbarComponent, ErrorComponent, PageRibbonComponent, ActiveMenuDirective, FooterComponent],
  bootstrap: [MainComponent],
})
export class AhomeAppModule {}
