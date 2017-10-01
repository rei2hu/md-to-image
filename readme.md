I had the sudden need to send lots of md and html
to a service with a 2000 character limit (yes Discord).

So why not just send an image?
1. html -> image
2. md -> html -> image

One possible solution is to use [Puppeteer](https://github.com/GoogleChrome/puppeteer) and its screenshot function but I can't run that on my server since it has low specs. Also I only want part of page because webscraping + extracting only part of the page's source.

So enter this. A super stripped html parser and renderer. Eh I'm still working on it.

TODO:
* Parsing
    * Handle *empty* tags
    * Replace entities in content
    * Add handling for more tags
* Rendering
    * Determine image height automatically
    * Make renderer render better
    * Handle inline content properly
    * Organization improvements
    * Scale inner images based on width
    * Font options
    * Other aesthetic options
* Other
    * Documentation
