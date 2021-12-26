import {NgModule} from '@angular/core';
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {BrowserModule} from '@angular/platform-browser';
import {FormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";

import {ButtonModule} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {DialogModule} from "primeng/dialog";
import {DropdownModule} from "primeng/dropdown";
import {InputNumberModule} from "primeng/inputnumber";
import {InputTextModule} from "primeng/inputtext";
import {MenubarModule} from "primeng/menubar";
import {MessageService} from "primeng/api";
import {RippleModule} from "primeng/ripple";
import {TableModule} from "primeng/table";
import {TabMenuModule} from "primeng/tabmenu";
import {ToastModule} from "primeng/toast";
import {ToolbarModule} from "primeng/toolbar";

import {AppComponent} from './app.component';
import {TopBarComponent} from './top-bar/top-bar.component';
import {DataTableComponent} from './data-table/data-table.component';
import {BackendMovieService} from "./backend-movie-service/backend-movie.service";

@NgModule({
  declarations: [
    AppComponent,
    TopBarComponent,
    DataTableComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ButtonModule,
    CalendarModule,
    DialogModule,
    DropdownModule,
    FormsModule,
    HttpClientModule,
    InputNumberModule,
    InputTextModule,
    MenubarModule,
    RippleModule,
    TableModule,
    TabMenuModule,
    ToastModule,
    ToolbarModule
  ],
  providers: [
    MessageService,
    BackendMovieService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
