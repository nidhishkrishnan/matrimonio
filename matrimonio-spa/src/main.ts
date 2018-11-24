import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import './polyfills';
import { AppModule } from './app/app.module';

platformBrowserDynamic().bootstrapModule(AppModule);