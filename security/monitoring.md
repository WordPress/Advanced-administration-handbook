# Monitoring

Site monitoring systems and services can notify you when your site isn't working properly. They can often correct any minor issues, or help you to do so before they become major issues.

## Uptime Monitoring

Uptime monitoring is traditionally done at the server level or by checking one or more URLs on the site at regular intervals to make sure they are responding properly. A combination of internal and external uptime monitoring is ideal for users, and there exist a variety of software and services to handle this for you.

## Performance Monitoring

While a site's services may be responding, to a user, a site being "up" means more than this to them. Performance monitoring is similar to uptime monitoring, but also takes note of certain metrics that could indicate trouble. Metrics like "page load time" and "slowest average transactions" should be monitored and reported regularly to help keep you ahead of performance issues. Monitoring slow logs for problematic queries or requests can also help keep user sites stable. MySQL, PHP-FPM, and others provide options to capture these for monitoring.

## Performance Profiling

It is best practice to use performance profiling tools, such as New Relic, AppDynamics or Tideways, to diagnose the performance bottlenecks of your website and infrastructure. These tools will give you insight such as slow performing functions, external HTTP requests, slow database queries and more that are causing poor performance.

## Changelog

- 2023-05-29: Updated from [Hosting Handbook](https://make.wordpress.org/hosting/handbook/reliability/#monitoring)
- 2023-03-04: Add new file.