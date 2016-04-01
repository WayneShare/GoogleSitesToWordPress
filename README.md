# GoogleSitesToWordPress By Wayne.

Ａ tool to help convert your google sites pages to the WordPress posts.

1. Export your google sites pages into a folder，use the tool of google-sites-liberation
   at this link https://github.com/sih4sing5hong5/google-sites-liberation
   by this tool, each page on sites will be created a index.html in a folder or subfolder.

2. Find out your WordPress accounts and URL for XMLRPC API.

3. Make the config properties files in our code, update the ToolConfig.properties file to your settings.

4. Try to run the GoogleToWordPress.class to let it:
  a. search your folder to find out all index.html file 
  b, extract the html content of your sites pages and convert them to posts for WordPress.
  c. write to WordPress via XMLRPC API.
  
5. Feel free to change the code upon the needs. Java code is really simple and we use the lib of jwordpress of Bican to access  the WordPress
