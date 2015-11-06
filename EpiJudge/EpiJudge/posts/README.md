Every problem is specified using this format. 

The data in the post can be viewed as a dictionary.
The key-value pairs are seperated by "\n--\n". The first string (after trimming) is the key,
rest is value.
You can put HTML in the value.
Linking to additional resources, like images, are fine. 
These resources should be in the folder called public. 

We use swig to fill in post.html, which is templatized by the keys in the dictionary created in this way.

See dnf.post for a good example of how we represent a problem.

