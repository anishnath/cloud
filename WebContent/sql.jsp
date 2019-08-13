<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
	"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>

<title>Mysql service online</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="Get a mysql database online">
<meta name="author" content="Anish Nath">
<link href="css/simple-sidebar.css" rel="stylesheet">

<%@ include file="analytics.jsp"%>
<!-- Include one of jTable styles. -->


<!-- <link href="css/jtable.css" rel="stylesheet" type="text/css" /> -->
<link href="css/jquery-ui-1.10.3.custom.css" rel="stylesheet" type="text/css" />
<!-- Include jTable script file. -->
<script src="js/jquery-1.8.2.js" type="text/javascript"></script>
<!--  <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->
<script src="js/jquery-ui-1.10.3.custom.js" type="text/javascript"></script>
<script src="js/jquery.jtable.js" type="text/javascript"></script>

 <!-- Bootstrap core CSS -->
<link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

<style>
/* jTable metro style theme - Blue
 * Created by Halil İbrahim Kalkan
 * http://www.jtable.org
 */
@font-face {
  font-family: 'Open Sans';
  font-style: normal;
  font-weight: 300;
  src: local('Open Sans Light'), local('OpenSans-Light'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/DXI1ORHCpsQm3Vp6mXoaTRa1RVmPjeKy21_GQJaLlJI.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: italic;
  font-weight: 300;
  src: local('Open Sans Light Italic'), local('OpenSansLight-Italic'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/PRmiXeptR36kaC0GEAetxrsuoFAk0leveMLeqYtnfAY.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: normal;
  font-weight: 400;
  src: local('Open Sans'), local('OpenSans'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/u-WUoqrET9fUeobQW7jkRT8E0i7KZn-EPnyo3HZu7kw.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: italic;
  font-weight: 400;
  src: local('Open Sans Italic'), local('OpenSans-Italic'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/xjAJXh38I15wypJXxuGMBtIh4imgI8P11RFo6YPCPC0.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: normal;
  font-weight: 600;
  src: local('Open Sans Semibold'), local('OpenSans-Semibold'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/MTP_ySUJH_bn48VBG8sNSha1RVmPjeKy21_GQJaLlJI.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: italic;
  font-weight: 600;
  src: local('Open Sans Semibold Italic'), local('OpenSans-SemiboldItalic'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/PRmiXeptR36kaC0GEAetxmWeb5PoA5ztb49yLyUzH1A.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: normal;
  font-weight: 700;
  src: local('Open Sans Bold'), local('OpenSans-Bold'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/k3k702ZOKiLJc3WVjuplzBa1RVmPjeKy21_GQJaLlJI.woff) format('woff');
}
@font-face {
  font-family: 'Open Sans';
  font-style: italic;
  font-weight: 700;
  src: local('Open Sans Bold Italic'), local('OpenSans-BoldItalic'), url(http://themes.googleusercontent.com/static/fonts/opensans/v6/PRmiXeptR36kaC0GEAetxoUt79146ZFaIJxILcpzmhI.woff) format('woff');
}
div.jtable-main-container {
  position: relative;
}
div.jtable-main-container div.jtable-title {
  position: relative;
  text-align: left;
}
div.jtable-main-container div.jtable-title .jtable-close-button {
  right: 0px;
  top: 0px;
  bottom: 0px;
  position: absolute;
}
div.jtable-main-container div.jtable-title div.jtable-toolbar {
  bottom: 0px;
  right: 0px;
  position: absolute;
  display: inline-block;
  margin-right: 5px;
}
div.jtable-main-container div.jtable-title div.jtable-toolbar span.jtable-toolbar-item {
  position: relative;
  display: inline-block;
  margin: 0px 0px 0px 5px;
  cursor: pointer;
  font-size: 0.9em;
  padding: 2px;
  vertical-align: bottom;
}
div.jtable-main-container div.jtable-title div.jtable-toolbar span.jtable-toolbar-item span.jtable-toolbar-item-icon {
  display: inline-block;
  margin: 2px;
  vertical-align: middle;
  width: 16px;
  height: 16px;
}
div.jtable-main-container div.jtable-title div.jtable-toolbar span.jtable-toolbar-item span.jtable-toolbar-item-text {
  display: inline-block;
  margin: 2px;
  vertical-align: middle;
}
div.jtable-main-container div.jtable-title .jtable-close-button + div.jtable-toolbar {
  margin-right: 30px;
}
div.jtable-main-container table.jtable {
  width: 100%;
}
div.jtable-main-container table.jtable thead th {
  vertical-align: middle;
  text-align: left;
}
div.jtable-main-container table.jtable thead th.jtable-column-header div.jtable-column-header-container {
  position: relative;
}
div.jtable-main-container table.jtable thead th.jtable-column-header div.jtable-column-header-container span.jtable-column-header-text {
  display: inline-block;
}
div.jtable-main-container table.jtable thead th.jtable-column-header div.jtable-column-header-container div.jtable-column-resize-handler {
  position: absolute;
  height: 24px;
  width: 8px;
  right: -8px;
  top: -2px;
  z-index: 2;
  cursor: col-resize;
}
div.jtable-main-container table.jtable thead th.jtable-command-column-header {
  text-align: center;
}
div.jtable-main-container table.jtable thead th.jtable-column-header-selecting {
  text-align: center;
  width: 1%;
}
div.jtable-main-container table.jtable thead th.jtable-column-header-selecting input {
  cursor: pointer;
}
div.jtable-main-container table.jtable thead th.jtable-column-header-sortable {
  cursor: pointer;
}
div.jtable-main-container table.jtable tbody tr > td .jtable-command-button {
  margin: 0px;
  padding: 0px;
  cursor: pointer;
  border: none;
  display: inline;
}
div.jtable-main-container table.jtable tbody tr > td .jtable-command-button span {
  display: none;
}
div.jtable-main-container table.jtable tbody tr > td.jtable-command-column {
  text-align: center;
  vertical-align: middle;
}
div.jtable-main-container table.jtable tbody tr > td.jtable-selecting-column {
  text-align: center;
  vertical-align: middle;
}
div.jtable-main-container table.jtable tbody tr > td.jtable-selecting-column input {
  cursor: pointer;
}
div.jtable-main-container table.jtable tbody tr.jtable-no-data-row {
  text-align: center;
}
div.jtable-main-container > div.jtable-bottom-panel {
  position: relative;
  min-height: 24px;
  text-align: left;
}
div.jtable-main-container > div.jtable-bottom-panel div.jtable-right-area {
  right: 0px;
  top: 0px;
  bottom: 0px;
  position: absolute;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list {
  display: inline-block;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-space,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-first,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-last,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-previous,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-next,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-active {
  padding: 2px 5px;
  display: inline-block;
  cursor: pointer;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-space,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-active,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-disabled {
  cursor: default;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-page-size-change {
  margin-left: 5px;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-goto-page {
  margin-left: 5px;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-goto-page input[type=text] {
  width: 22px;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-page-info {
  vertical-align: middle;
}
div.jtable-main-container div.jtable-column-resize-bar {
  opacity: 0.5;
  filter: alpha(opacity=50);
  position: absolute;
  display: none;
  width: 1px;
  background-color: #000;
}
div.jtable-main-container div.jtable-column-selection-container {
  position: absolute;
  display: none;
  border: 1px solid #C8C8C8;
  background: #fff;
  color: #000;
  z-index: 101;
  padding: 5px;
}
div.jtable-main-container div.jtable-column-selection-container ul.jtable-column-select-list {
  margin: 0px;
  padding: 0px;
  list-style: none;
}
div.jtable-main-container div.jtable-column-selection-container ul.jtable-column-select-list li {
  margin: 0px;
  padding: 2px 0px;
}
div.jtable-main-container div.jtable-column-selection-container ul.jtable-column-select-list li label span {
  position: relative;
  top: -1px;
  margin-left: 4px;
}
div.jtable-main-container div.jtable-column-selection-container ul.jtable-column-select-list li input[type="checkbox"] {
  cursor: pointer;
}
form.jtable-dialog-form div.jtable-input-field-container {
  padding: 2px 0px 3px 0px;
  border-bottom: 1px solid #ddd;
}
form.jtable-dialog-form div.jtable-input-field-container:last-child {
  border: none;
}
form.jtable-dialog-form div.jtable-input-label {
  padding: 2px 3px;
  font-size: 1.1em;
  color: #666;
}
form.jtable-dialog-form div.jtable-input {
  padding: 2px;
}
form.jtable-dialog-form div.jtable-date-input {
  /* No additional style */

}
form.jtable-dialog-form div.jtable-text-input {
  /* No additional style */

}
form.jtable-dialog-form span.jtable-option-text-clickable {
  position: relative;
  top: -2px;
}
form.jtable-dialog-form div.jtable-textarea-input textarea {
  width: 300px;
  min-height: 60px;
}
form.jtable-dialog-form div.jtable-checkbox-input span,
form.jtable-dialog-form div.jtable-radio-input span {
  padding-left: 4px;
}
form.jtable-dialog-form div.jtable-radio-input input,
form.jtable-dialog-form div.jtable-checkbox-input input,
form.jtable-dialog-form span.jtable-option-text-clickable {
  cursor: pointer;
}
div.jtable-busy-panel-background {
  opacity: 0.1;
  filter: alpha(opacity=50);
  z-index: 998;
  position: absolute;
  background-color: #000;
}
div.jtable-busy-panel-background.jtable-busy-panel-background-invisible {
  background-color: transparent;
}
div.jtable-busy-message {
  cursor: wait;
  z-index: 999;
  position: absolute;
  margin: 5px;
}
div.jtable-contextmenu-overlay {
  position: fixed;
  left: 0px;
  top: 0px;
  width: 100%;
  height: 100%;
  z-index: 100;
}
div.jtable-main-container {
  font-family: 'Segoe UI Semilight', 'Open Sans', Verdana, Arial, Helvetica, sans-serif;
  font-weight: 300;
  font-size: 14px;
  background: #fff;
  line-height: 1.3;
}
div.jtable-main-container > div.jtable-title {
  background-color: #0b67cd;
  padding-left: 10px;
}
div.jtable-main-container > div.jtable-title div.jtable-title-text {
  font-family: 'Segoe UI Semilight', 'Open Sans', Verdana, Arial, Helvetica, sans-serif;
  font-weight: 300;
  font-size: 19px;
  line-height: 34px;
  color: #fff;
}
div.jtable-main-container > div.jtable-title .jtable-close-button {
  right: 8px;
  top: 8px;
  bottom: 8px;
  position: absolute;
  opacity: 0.5;
  filter: alpha(opacity=50);
  background: url('css/metro/close.png') no-repeat;
  width: 16px;
  height: 16px;
}
div.jtable-main-container > div.jtable-title .jtable-close-button:hover {
  opacity: 1;
  filter: alpha(opacity=50);
}
div.jtable-main-container > div.jtable-title div.jtable-toolbar {
  bottom: 0px;
  right: 0px;
  position: absolute;
}
div.jtable-main-container > div.jtable-title div.jtable-toolbar span.jtable-toolbar-item {
  background-color: #1571d7;
  color: white;
}
div.jtable-main-container > div.jtable-title div.jtable-toolbar span.jtable-toolbar-item.jtable-toolbar-item-add-record span.jtable-toolbar-item-icon {
  background-image: url(<%=request.getContextPath()%>'/css/metro/add1.png');
}
div.jtable-main-container > div.jtable-title div.jtable-toolbar span.jtable-toolbar-item.jtable-toolbar-item-hover {
  background-color: #1c78de;
  padding-bottom: 6px;
}
div.jtable-main-container > table.jtable {
  border: 1px solid #2d89ef;
  border-collapse: collapse;
  border-spacing: 0;
}
div.jtable-main-container > table.jtable > thead {
  background-color: #2d89ef;
}
div.jtable-main-container > table.jtable > thead th {
  font-family: 'Segoe UI Semilight', 'Open Sans', Verdana, Arial, Helvetica, sans-serif;
  font-weight: 300;
  font-size: 15px;
  color: #fff;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container {
  height: 24px;
  margin-left: 4px;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container div.jtable-column-resize-handler {
  height: 28px;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header div.jtable-column-header-container span.jtable-column-header-text {
  margin-top: 2px;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header-sortable div.jtable-column-header-container {
  background: url('css/metro/column-sortable.png') no-repeat right;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header-sorted-asc div.jtable-column-header-container {
  background: url('css/metro/column-asc.png') no-repeat right;
}
div.jtable-main-container > table.jtable > thead th.jtable-column-header-sorted-desc div.jtable-column-header-container {
  background: url('css/metro/column-desc.png') no-repeat right;
}
div.jtable-main-container > table.jtable > tbody > tr {
  background-color: #fff;
}
div.jtable-main-container > table.jtable > tbody > tr > td {
  border: 1px solid #ddd;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td {
  padding: 4px;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td > .jtable-edit-command-button {
  background: url('css/metro/edit.png') no-repeat;
  width: 16px;
  height: 16px;
  opacity: 0.4;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td > .jtable-edit-command-button:hover {
  opacity: 0.8;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td > .jtable-delete-command-button {
  background: url('css/metro/delete.png') no-repeat;
  width: 16px;
  height: 16px;
  opacity: 0.4;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-data-row > td > .jtable-delete-command-button:hover {
  opacity: 0.8;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-row-even {
  background-color: #f9f9f9;
}
div.jtable-main-container > table.jtable > tbody > tr:hover {
  background: #e8eaef;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-row-selected {
  color: #fff;
  background-color: #4fabff;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-row-created {
  background-color: #60bcff;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-row-updated {
  background-color: #60bcff;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-row-deleting {
  background-color: #60bcff;
  color: #fff;
}
div.jtable-main-container > table.jtable > tbody > tr.jtable-child-row > td {
  padding: 2px;
  background-color: #fff;
}
div.jtable-main-container > div.jtable-bottom-panel {
  background-color: #1c78de;
  color: #fff;
  min-height: 22.900000000000002px;
  font-size: 13px;
  border: 1px solid #2d89ef;
  border-top: none;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list {
  margin: 1px 0px 0px 0px;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-space,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-first,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-last,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-previous,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-next,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-active {
  background-color: #2d89ef;
  margin: 1px;
  padding: 2px 5px;
  color: #fff;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number:hover,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-first:hover,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-last:hover,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-previous:hover,
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-next:hover {
  background-color: #4fabff;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-disabled {
  opacity: 0.75;
  filter: alpha(opacity=50);
  color: #ccc;
  cursor: default;
}
div.jtable-main-container > div.jtable-bottom-panel .jtable-page-list .jtable-page-number-disabled:hover {
  background-color: #2d89ef;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-page-info {
  display: inline-block;
  padding: 4px;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-page-size-change {
  display: inline-block;
  padding: 2px 0px 2px 0px;
}
div.jtable-main-container > div.jtable-bottom-panel span.jtable-goto-page {
  display: inline-block;
  padding: 2px 0px 2px 0px;
}
form.jtable-dialog-form {
  font-family: 'Segoe UI Semilight', 'Open Sans', Verdana, Arial, Helvetica, sans-serif;
  font-weight: 400;
  font-size: 14px;
}
div.jtable-busy-message {
  font-family: 'Segoe UI Semilight', 'Open Sans', Verdana, Arial, Helvetica, sans-serif;
  font-weight: 300;
  font-size: 16px;
  border: 1px solid #fff;
  padding: 5px 5px 5px 58px;
  color: #fff;
  background: url('css/metro/loading.gif') no-repeat;
  background-color: #0b67cd;
  background-position: 8px;
}

</style>

 

<script type="text/javascript">
    $(document).ready(function() {
        $('#dockerContainer').jtable({
            title : 'Your Data Base List',
            actions : {
                listAction : 'MYSQLServlet?action=list',
                createAction : 'MYSQLServlet?action=create',
                updateAction : 'MYSQLServlet?action=update',
                deleteAction : 'MYSQLServlet?action=delete'
            },
            fields : {
                id : {
                    title : 'id',
                    width : '10%',
                    key : true,
                    list : true,
                    edit : false,
                    create : false
                },
                dbname : {
                    title : 'DB Name',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                dbusername : {
                    title : 'DB User Name',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                password : {
                    title : 'DB Password',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                status : {
                    title : 'STATUS',
                    width : '10%',
                    list : true,
                    edit : false,
                    create : false
                },
                Timestamp : {
                    title : 'Time Stamp',
                    width : '20%',
                    list : true,
                    edit : false,
                    create : false
                },
            }
        });
        $('#dockerContainer').jtable('load');
    });
</script>


</head>
<body>





<%@ include file="navigation.jsp"%>

<div></div>
<hr>


 <%
                              
                              String error = (String)request.getAttribute("error");
                              if(error!=null)
                              {
                            	  %>
                              
                              <p><font color="red"><%=error %></font></p>
                              
                              <% } %>
                              


  <div class="d-flex" id="wrapper">

    <!-- Sidebar -->

<div class="bg-light border-right" id="sidebar-wrapper">
       <div class="sidebar-heading"></div>
      
      <div class="list-group list-group-flush">
         <a href="index.jsp" class="list-group-item list-group-item-action bg-light">Home</a>
      </div>
      
       <div class="list-group list-group-flush">
        <a href="login.jsp" class="list-group-item list-group-item-action bg-light">Login</a>
      </div>
      

      
      <div class="list-group list-group-flush">
        
      </div>
      
      <div class="list-group list-group-flush">
        
      </div>

     <div class="list-group list-group-flush">
        <a href="add.jsp" class="list-group-item list-group-item-action bg-light">Container Service</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="sql.jsp" class="list-group-item list-group-item-action bg-light">MySQL Service</a>
      </div>
      
	<div class="list-group list-group-flush">
        <a href="contactus.jsp" class="list-group-item list-group-item-action bg-light">Contact Me</a>
      </div>
      
      <div class="list-group list-group-flush">
        <a href="https://www.linkedin.com/in/anishnath" class="list-group-item list-group-item-action bg-light">Hire Me!!</a>
      </div>
       <div class="list-group list-group-flush">
        <a href="https://8gwifi.org" class="list-group-item list-group-item-action bg-light">Cryptography Playground</a>
      </div>
      
    </div>
    <!-- /#sidebar-wrapper -->

    <!-- Page Content -->
    <div id="page-content-wrapper">

      <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <button class="btn btn-primary" id="menu-toggle"> Menu</button>

        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>

<!--         <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav ml-auto mt-2 mt-lg-0">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
            <li class="nav-item dropdown">
              <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Dropdown
              </a>
              <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdown">
                <a class="dropdown-item" href="#">Action</a>
                <a class="dropdown-item" href="#">Another action</a>
                <div class="dropdown-divider"></div>
                <a class="dropdown-item" href="#">Something else here</a>
              </div>
            </li>
          </ul>
        </div> -->
      </nav>
      
      <h2 class="mt-4">MYSQL Service</h2>
      

      <div class="container-fluid">
      
       
                              
      
       <div id="dockerContainer"></div>
       
       <div class="embed-responsive embed-responsive-16by9">
  <iframe class="embed-responsive-item" src="http://localhost:8089/" allowfullscreen></iframe>
</div>
       
			
      </div>
    </div>
    <!-- /#page-content-wrapper -->

  </div>
  <!-- /#wrapper -->

  <!-- Bootstrap core JavaScript -->
 
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 <!--  <script src="http://code.jquery.com/jquery-1.9.1.js"></script> -->

  <!-- Menu Toggle Script -->
  <script>
    $("#menu-toggle").click(function(e) {
      e.preventDefault();
      $("#wrapper").toggleClass("toggled");
    });
  </script>

		

		
	

	





<div class="lefttop"> </div>



</div>

</body>
</html>