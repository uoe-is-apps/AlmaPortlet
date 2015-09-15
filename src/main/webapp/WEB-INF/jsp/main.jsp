<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License. You may obtain a
    copy of the License at:

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on
    an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied. See the License for the
    specific language governing permissions and limitations
    under the License.

--%>

<jsp:directive.include file="/WEB-INF/jsp/include.jsp"/>
<c:set var="n"><portlet:namespace/></c:set>

<link type="text/css" rel="stylesheet" href="<c:url value="/css/library-account.css"/>" />
<%--<script src="/web_files/js/jquery-1.11.1.min.js" type="text/javascript"></script>
<script src="/web_files/js/easyResponsiveTabs.js" type="text/javascript"></script>
<script src="/web_files/js/jquery.ba-resize.min.js" type="text/javascript"></script>
<script src="/web_files/js/jquery.responsive-containers.js" type="text/javascript"></script>--%>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.4/angular.min.js" type="text/javascript"></script>
<script src="<c:url value="/js/app.js"/>" type="text/javascript"></script>
<div ng-app="libraryDetailsApp">
<div ng-controller="libraryDetailsController" id="${n}topDiv" class="page-style">
 
     <div ng-if="errorMessage" id="${n}error" ng-cloak>
     	<!--  system or patron error occurred -->
     	<div class="row">
            <div class="col-md-12">
           	    <div class="alert alert-warning">
           		    <strong>Warning:</strong>  {{errorMessage}}
           		</div>
           	</div>
        </div>
     </div>

     <div id="${n}library" ng-cloak>

        <div class="library-login">
            <div align="right">
            <a href="${basicLoginUrl}" target="_blank">
            <button class="btn btn-blue" onclick="loginToLibrary()"><span class="fa fa-sign-in">&#160;</span><spring:message code="main.loginButton" />
            </button>
            </a>
            </div>   
            <br/>
            <div align="right">
            Login to your Library Account to view full details of Requests
            </div>   
            <br/>
        </div>    

        <div style="clear: both;">&nbsp;</div>
        
         <div ng-if="libraryData" id="libraryAccountTabs${n}">
        	<ul class="nav nav-tabs responsive">
        		<li class="active"><a data-toggle="tab" href="#${n}loanItemsTab"><spring:message code="main.loansHeader" />
        			<span id="${n}loanTotal" class="library-badge">{{libraryData.totalLoans}}</span></a>
        		</li>
        		
        		<li><a data-toggle="tab" href="#${n}requestedItemsTab"><spring:message code="main.requestsHeader" />
        			<span id="${n}requestTotal" class="library-badge">{{libraryData.totalRequests}}</span></a>
        		</li>
        		
        		<li><a data-toggle="tab" href="#${n}finesTab"><spring:message code="main.finesHeader" />
        			<span id="${n}fineTotal" class="library-badge">{{libraryData.totalFines}}</span></a>
        		</li>
        	</ul>
        	<div class="tab-content">
        	<div class="tab-pane active" id="${n}loanItemsTab">
        		<div class="table-responsive">
        			<table id="${n}loanItemsTable" class="table">
        				<thead>
                            <tr>
                                <th><spring:message code="main.itemTitle" /></th>
                            	<th><spring:message code="main.statusTitle" /></th>
                            	<th><spring:message code="main.dueDateTitle" /></th>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr ng-repeat="loanItem in libraryData.loanItems">
                        		<td>{{loanItem.title}}</td>
                        		<td>{{loanItem.statusText}}</td>
                        		<td>{{loanItem.dueDateConverted}}</td>
                        	</tr>
                        </tbody>
        			</table>
        		</div>
        	</div>
        	<div class="tab-pane" id="${n}requestedItemsTab">
        		<div class="table-responsive">
        			<table id="${n}requestedItemsTable" class="table">
        				<thead>
                              <tr>
                                 <th><spring:message code="main.itemTitle" /></th>
                            	 <th><spring:message code="main.databaseTitle" /></th>
                              </tr>
                        </thead>
                        <tbody>
                        	<tr ng-repeat="requestItem in libraryData.requestItems">
                            	<td>{{requestItem.itemTitle}}</td>
                            	<td>{{requestItem.pickupLocation}}</td>
                            </tr>
                        </tbody>
        			</table>
        		</div>
        	</div>
        	<div class="tab-pane" id="${n}finesTab">
        		<div class="table-responsive">
        			<table id="${n}finesTable" class="table">
        				<thead>
                              <tr>
                                 <th><spring:message code="main.dateTitle" /></th>
                            	 <th><spring:message code="main.itemTitle" /></th>
                            	 <th><spring:message code="main.feeTypeTitle" /></th>
                            	 <th><spring:message code="main.feeTitle" /></th>
                              </tr>
                        </thead>
                        <tbody>
                        	<tr ng-repeat="fine in libraryData.fines">
                        	   <td>{{fine.dateConverted}}</td>
                        	   <td>{{fine.title}}</td>
                        	   <td>{{fine.fineType}}</td>
                        	   <td>{{fine.amount}}</td>
                        	</tr>
                        </tbody>
        			</table>
        		</div>      		
        	</div>
        	</div>
        </div>
     </div> 
     
</div>
</div>
      