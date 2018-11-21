/*import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';*/
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './home/home.component';
import { SharedModule } from './shared/shared.module';
import { HeaderComponent } from './shared/layout/header/header.component';
import { ProfileComponent } from './profile/profile.component';
import { OrderModule } from 'ngx-order-pipe';
import { QuestionPipe } from './shared/pipes/question/question.pipe';
import { SelectModule } from 'ng2-select';
import { LoaderComponent } from './shared/loader/loader.component';
import { Ng5SliderModule } from 'ng5-slider';
import { RouterModalModule } from 'ng-bootstrap-modal';


@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    HeaderComponent,
    ProfileComponent,
    LoaderComponent,
    QuestionPipe
  ],
  imports: [
    Ng5SliderModule,
    AppRoutingModule,
    SharedModule,
    OrderModule,
    SelectModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }