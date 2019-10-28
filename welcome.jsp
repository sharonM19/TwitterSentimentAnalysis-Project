<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>

<head>
<title>Twitter Sentiment Analysis</title>
<link href="/content/styles.css" rel="stylesheet" type="text/css">
</head>


<div class="wrapper fadeInDown">
  <div id="formContent">
    <!-- Tabs Titles -->
    <h1 class="active"> Twitter Sentiment Analysis </h3>
    <br/>
    <h5>Please input the parameter you want to search </h1>
    <form action="results" name="results" method="POST">
     <!--  <input type="text" id="twitterInput" name="twitterInput" />-->
      
      <input type="text" id="twitterInput" class="fadeIn second" name="twitterInput">
      <input type="submit" class="fadeIn fourth" value="Submit">
    </form>
  </div>
  </div>
</html>

