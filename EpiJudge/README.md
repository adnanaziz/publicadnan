EpiJudge contains a node/express webapp for displaying programming 
problems and checking user-submitted solutions.

To run the webapp the first time you need nodejs. Once that's installed, run 

npm install 

(This downloads the libraries the webapp uses, it's a one time operation.)

To run the webapp type 

node app.js

(Top tip: use supervisor instead of node, that way changes to the project as well
as crashes lead to the webapp being relaunched.)

Point your browser to localhost:3000/post/linked-list-delete and localhost:3000/post/parity-basic to see examples.

Go to the posts subfolder. See how linked-list-delete and parity-basic are specified
as single text files, with summary, description, tags, hint, skeleton code, and test cases.
Use the same format for new problems.

The backend is implemented using compilebox. Specifically, epijudge.ddns.net/compile
accepts HTTP POST requests. It compiles and runs submitted code, which consists of user-provided
code and the tests. The tests are sent to the web-browser, but hidded from the user. The compilebox service returns the result of in the form of a JSON object; fields include output, error, and time fields. The first field, output, is what was printed to stdout. The second field, error, 
is what was written to stderr, this includes compile failures, and runtime errors. The python
file compilebox_api_demo.py shows the details of the compilebox API.

The key reason we use compilebox is that it launches the build and run job in a docker-generated
VM. This solves the problem of protecting the server from bad/malicious inputs. 
In addition to sanboxing requests, it also can timeout requests.

Another nice feature of compilebox is that its own frontend is written in node/express, and
it includes a rate-limiting library. The server will block excessive number of requests 
from a single IP address. By my calculations, we can easily support a request per second.

Architecturally, requests for posts go to /post/:postId. 
The node/express webapp reads the problem description from the file named postId,
and parses that description into a dictionary that is used by Swig to render the post
from the template in views/templates/post.html. The {{ foo }} notation in that file
refers to the field in the dictionary.

The post.html file uses Bootstrap gridding to create the layout.
It includes Javascript calls to populate the sidebars; currently these calls hit the webapp
which uses mock data to return trending tags, related posts, articles, etc.

Code is submitted by an AJAX call. The callback updates the output, error and time divs in the page.

Search is also currently mocked. It overwrites the post div 
(the 8 columns in the middle) with the search result.

TODOs, in no particular order

  - Good taxonomy of problems, easy to access
  - Implement mocked API calls
  - Decide on right page layout
  - Decide on right fields for templatizing post
  - Figure out a good way to embed the discussion. My thought is to 
    use the right 4 columns to bring up discourse (or disqus).
  - Add an optional login. Passport is ideal, I know it well. 
    It will work with Google, FB, GitHub, etc., and allows for custom login too.
    - Management page of submissions and their status
    - Leaderboard for problems, statistics
  - Make hints hidden
  - Fix bug with args (needs compilebox work)



