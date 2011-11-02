//function to initialize the page
var init = function() {
//  $('div.login-ctr').show();
//  $('div.login-ctr').hide();
  $('div.managealert-ctr').hide();
  $('div.addalert-ctr').hide();
  $('div.testalert-ctr').hide();
  $('div.viewalert-ctr').hide();
  $('div.google-login-ctr').show();
  googleUserLogin();
  initFB();
}

function getUsername() {
  return document.getElementById('managealert-ctr-username').value;
}

//function userLogout() {
//  $('div.login-ctr').show();
//  $('div.managealert-ctr').hide();
//  $('div.addalert-ctr').hide();
//  $('div.testalert-ctr').hide();
//  $('div.viewalert-ctr').hide();  
//  document.getElementById('managealert-ctr-username').value = '';
//  init();
//}

var googleUserLogin = function() {
  // sticky div block
  var parameter=new Array();
  parameter[parameter.length]=new param('action', 'newsession');
  $.ajax({
    url : "/googlelogin",
    type : "GET",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
        $('div.global-show-message').show().html(resp);
        // sample resp: "<p>Hello peter! <a href..."
        if (resp.indexOf("<p>") == 0) {
          var username = resp.substring(9, resp.indexOf("! <a href="));
          document.getElementById('managealert-ctr-username').value = username;
          goToManageAlert();
        }
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("googleUserLogin(): failed to call ajax");
    }
  });

}

//Check if the login and password is from APT staff or me.
//function userLogin() {
//  var hasError = false;
//  var nameVal = $('#username').val();
//  var passVal = $('#password').val();
//  if (nameVal == '' || (nameVal != "adnan.aziz" && nameVal != "kamran.ks" && nameVal != "kj") || passVal != "apt") {
//    showMessage("Currently only open for APT!  Please check your username and password.", "login");
//    return;
//  }
//  manageAlert(nameVal);
//}

//function goToLogin() {
//  $('div.login-ctr').show();
//  $('div.managealert-ctr').hide();
//  $('div.addalert-ctr').hide();
//  $('div.testalert-ctr').hide();
//  $('div.viewalert-ctr').hide();
//}

//var showMessage = function(message, entity){
//  $('#'+entity+'-show-message').show().html('<p><b>'+message+'</b></p>');
//}

function goToManageAlert() {
//  $('div.login-ctr').hide();
  $('div.managealert-ctr').show();
  $('div.addalert-ctr').hide();
  $('div.testalert-ctr').hide();
  $('div.viewalert-ctr').hide();
  username = getUsername();
  manageAlert(username);
}

var manageAlert = function(username) {
//  var greeting = "<input type=\"hidden\" class=\"managealert-ctr\" id=\"managealert-ctr-username\" name=\"username\" value=\"" + username + "\">";
//  $('div.managealert-ctr-username').show().html(greeting);
  var parameter=new Array();
  parameter[parameter.length]=new param('username', username);
  parameter[parameter.length]=new param('action', "list");
  //making the ajax call
  $.ajax({
    url : "/listalert",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      $('#login-ctr').hide();
      $('div.managealert-ctr').show();
      if (resp!=''){
        $('div.managealert-table').show().html(resp);
      }
    },
    error : function(resp){
      $('#managealert-show-message').show().html("manageAlert(): failed to call ajax");
    }
  });
}

var goToAddAlert = function() {
  var username = document.getElementById('managealert-ctr-username').value;
//  var greeting = "<input type=\"hidden\" name=\"addalert-ctr-username\" value=\"" + username + "\">";
//  $('div.addalert-ctr-username').show().html(greeting);
//  $('div.login-ctr').hide();
  $('div.managealert-ctr').hide();
  $('div.testalert-ctr').hide();
  $('div.addalert-ctr').show();
}

var addAlert_do = function(form) {
  var parameter=new Array();
  var username = document.getElementById('managealert-ctr-username').value;
  parameter[parameter.length]=new param('username', username);
  var nameVal = form.addalert_name.value;
  parameter[parameter.length]=new param('alertName', nameVal);
  var origVal = form.addalert_orig.value;
  parameter[parameter.length]=new param('origin', origVal);
  var destVal = form.addalert_dest.value;
  parameter[parameter.length]=new param('dest', destVal);
  var monthYearVal = form.addalert_monthyear.value;
  parameter[parameter.length]=new param('monthYear', monthYearVal);
  var emailVal = form.addalert_email.value;
  parameter[parameter.length]=new param('email', emailVal);
  var ppmVal = form.addalert_ppm.value;
  parameter[parameter.length]=new param('targetppm', ppmVal);
  var expireDateVal = form.addalert_expiredate.value;
  parameter[parameter.length]=new param('expireDate', expireDateVal);
  var checkFreqVal = 'sixhours';  // default=6hr
  for (var i=0; i < form.checkFreq.length; i++)
  {
    if (form.checkFreq[i].checked)
    {
      checkFreqVal = form.checkFreq[i].value;
    }
  }
  parameter[parameter.length]=new param('checkFreq', checkFreqVal);

  if (origVal=='' || monthYearVal=='' || emailVal=='' || ppmVal=='' || expireDateVal=='') {
    alert("Some values are missing in the form!");
    return;
  }
  
  //making the ajax call
  $.ajax({
    url : "/createalert",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
//        $('div.global-show-message').show().html(resp);
        $('div.addalert-ctr').hide();
        testAlert(resp);
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("addAlert(): failed to call ajax");
    }
  });
}

var viewAlert=function(anchorID) {
  $('div.managealert-ctr').hide();
  var parameter=new Array();
  parameter[parameter.length]=new param('action', 'view');
  var username = document.getElementById('managealert-ctr-username').value;
  parameter[parameter.length]=new param('username', username);
  var alertID = anchorID.substring(4); // e.g. view12
//  $('div.global-show-message').show().html("View Alert ID: " + alertID);
  parameter[parameter.length]=new param('alertid', alertID);
  
  //making the ajax call
  $.ajax({
    url : "/viewalert",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
        $('div.viewalert-table').show().html(resp);
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("viewAlert(): failed to call ajax");
    }
  });
  $('div.viewalert-ctr').show();
}

var deleteAlert=function(anchorID) {
  var parameter=new Array();
  parameter[parameter.length]=new param('action', 'delete');
  var username = document.getElementById('managealert-ctr-username').value;
  parameter[parameter.length]=new param('username', username);
  var alertID = anchorID.substring(6); // e.g. delete12
//  $('div.global-show-message').show().html("Delete ID: " + alertID);
  parameter[parameter.length]=new param('alertid', alertID);
  
  //making the ajax call
  $.ajax({
    url : "/listalert",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
//        $('div.global-show-message').show().html(resp);
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("deleteAlert(): failed to call ajax");
    }
  });
  goToManageAlert();
}

var testAlert=function(anchorID) {
  var parameter=new Array();
  parameter[parameter.length]=new param('action', 'test');
  var username = document.getElementById('managealert-ctr-username').value;
  parameter[parameter.length]=new param('username', username);
  var alertID = anchorID.substring(4); // e.g. test12
//  $('div.global-show-message').show().html("Test ID: " + alertID);
  parameter[parameter.length]=new param('alertid', alertID);
  
  //making the ajax call
  $.ajax({
    url : "/testalert",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
        $('div.managealert-ctr').hide();
        $('div.testalert-table').show().html(resp);
        $('div.testalert-ctr').show();
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("testAlert(): failed to call ajax");
    }
  });
//  goToManageAlert();
}

//parameter object definition
var param=function(name,value){
  this.name=name;
  this.value=value;
}

function initFB() {
  FB.init({appId:'290100234351079', cookie:true, status:true, xfbml:true});

//  FB.Event.subscribe('auth.statusChange', function(response) {
//    alert("response=" + response);
//    alert("status=" + response.status);
//    alert("authResponse=" + response.authResponse);
//    alert("session=" + response.session);
//    if (response.authResponse) {
//      alert("accessToken=" + response.authResponse.accessToken + ", userid=" + response.authResponse.userID);
//    } else {
//      alert("Not connected");
//    }
//  });
  
//  FB.login(function (response) {
//    if (response.session) {
//        var access_token = response.session.access_token;
//        alert(access_token);
//    } else {
//        alert('User is logged out');
//    }
//  });

}

function checkFBLogin() {
  FB.getLoginStatus(function(response) {
    if (response.session) {
      access_token = response.session.access_token;
//      alert("getFBAccessToken=" + access_token);
      showFBSuggestions(access_token);
    } else {
      alert("No response.session!");
    }
  });  
}

function showFBSuggestions(access_token) {
  var parameter=new Array();
  var username = document.getElementById('managealert-ctr-username').value;
  parameter[parameter.length]=new param('username', username);
  parameter[parameter.length]=new param('accessToken', access_token);

  $.ajax({
    url : "/facebook",
    type : "POST",
    data:parameter,
    dataType:"html",
    success : function(resp) {
      if (resp!=''){
        $('div.fb-suggest-table').show().html(resp);
      }
    },
    error : function(resp){
      $('div.global-show-message').show().html("showFBSuggestions(): failed to call ajax");
    }
  });
}