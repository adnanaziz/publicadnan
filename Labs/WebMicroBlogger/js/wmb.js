var URLPATH = "http://localhost:16667/queryServlet?queryString=";

function likeposting() 
{ alert("likeposting is not implemented"); }

function likepostingauthor() 
{ alert("likepostingauthor is not implemented"); }

function deleteposting() 
{ alert("deleteposting is not implemented"); }

function gettrendingpostings() 
{ alert("gettrendingpostings is not implemented"); }

function queryposting() { 
  alert("queryposting is to implemented by student"); 
};
    
function submitposting() 
{
 var jsonreq = {};
 jsonreq["type"] = "CREATE";
 jsonreq["author"] = document.createform.elements["name"].value;
 jsonreq["subject"] = document.createform.elements["subject"].value;
 jsonreq["body"] = document.createform.elements["body"].value;
 jsonreq["latitude"] = document.createform.elements["latitude"].value;
 jsonreq["longitude"] = document.createform.elements["longitude"].value;
 jsonreq["date"] = document.createform.elements["date"].value;
 var jsonreqstring = JSON.stringify(jsonreq);

 $.getJSON(URLPATH + jsonreqstring, 
      function(data) {
       $("#ulpostinglist").children().remove();
       for (var i = 0, len = data.postings.length; i < len && i < 4; i++) {
         $("#ulpostinglist").append('<li>' + data.postings[i].author + ', ' 
                                  + data.postings[i].subject + ', '
                                  + data.postings[i].body + '</li>');
       }
     }
    )
    .error(function() { alert("error in submitposting" ); });
};
    
$(document).ready(function(){
  $("#btntest").click(function(){
    var serverMsg = {
      "id":0,
      "postings":[
          {"id":0,"date":"12/3/2012","latitude":"123.4","longitude":"34.41","body":"A random posting.","subject":"Hello World!","author":"Adnan Aziz","authorLikes":123,"postingUpvotes":12},
          {"id":0,"date":"12/2/2012","latitude":"123.4","longitude":"34.41","body":"My first posting","subject":"99.97!","author":"Don Bradman","authorLikes":123,"postingUpvotes":12},
          {"id":0,"date":"11/3/2012","latitude":"123.4","longitude":"34.41","body":"Why do I always lose the big games?","subject":"Number 1","author":"Graham Smith","authorLikes":123,"postingUpvotes":12},
          {"id":0,"date":"11/2/2012","latitude":"123.4","longitude":"34.41","body":"365 wickets, best in the world","subject":"All rounder!","author":"Ian Botham","authorLikes":939,"postingUpvotes":12},
          {"id":0,"date":"11/2/2012","latitude":"123.4","longitude":"34.41","body":"DSFD FDSF KFD.","subject":"Old timer","author":"Everton Weekes","authorLikes":13,"postingUpvotes":12},
          {"id":0,"date":"11/1/2012","latitude":"123.4","longitude":"34.41","body":"Empty.","subject":"Boring...","author":"Sunny Gavaskar","authorLikes":3443,"postingUpvotes":12}
      ]
    };
    // first clear the list 
    $("#ulpostinglist").children().remove();
    for (var i = 0, len = serverMsg.postings.length; i < len && i < 4; i++) {
      $("#ulpostinglist").append('<li>' + serverMsg.postings[i].author + ', ' 
                                  + serverMsg.postings[i].subject + ', '
                                  + serverMsg.postings[i].body + '</li>');
    }
  });
});
    
$(document).ready(function(){
  $("#btn0").click(function(){
    submitposting();
  });
});
    
$(document).ready(function(){
  $("#btn1").click(function(){
    queryposting();
  });
});
    
$(document).ready(function(){
  $("#btn2").click(function(){
    deleteposting();
  });
});
    
$(document).ready(function(){
  $("#btn3").click(function(){
    likeposting();
  });
});
    
$(document).ready(function(){
  $("#btn4").click(function(){
    likepostingauthor();
  });
});
    
$(document).ready(function(){
  $("#btn5").click(function(){
    gettrendingpostings();
  });
});
