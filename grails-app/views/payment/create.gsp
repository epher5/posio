<%--
  Created by IntelliJ IDEA.
  User: ephermendoza
  Date: 6/10/15
  Time: 10:36 PM
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title></title>
</head>

<body>

<h1>Charge with Simplify Commerce</h1>
<g:form id="simplify-payment-form" action="" method="POST" class="simpleform" url="[controller:'payment', action:'authorize']">
    <!-- The $10 amount is set on the server side -->
    <div>
        <label>Amount: </label>
        <input name="amount" id="cc-amount" type="text" maxlength="12" autocomplete="off" value="10.00"/>
    </div>   <div>
        <label>Credit Card Number: </label>
        <input id="cc-number" type="text" maxlength="20" autocomplete="off" value="" autofocus />
    </div>
    <div>
        <label>CVC: </label>
        <input id="cc-cvc" type="text" maxlength="4" autocomplete="off" value=""/>
    </div>
    <div>
        <label>Expiry Date: </label>
        <select id="cc-exp-month">
            <option value="01">Jan</option>
            <option value="02">Feb</option>
            <option value="03">Mar</option>
            <option value="04">Apr</option>
            <option value="05">May</option>
            <option value="06">Jun</option>
            <option value="07">Jul</option>
            <option value="08">Aug</option>
            <option value="09">Sep</option>
            <option value="10">Oct</option>
            <option value="11">Nov</option>
            <option value="12">Dec</option>
        </select>
        <select id="cc-exp-year">
            <option value="15">2015</option>
            <option value="16">2016</option>
            <option value="17">2017</option>
            <option value="18">2018</option>
            <option value="19">2019</option>
            <option value="20">2020</option>
            <option value="21">2021</option>
            <option value="22">2022</option>
            <option value="23">2023</option>
            <option value="24">2024</option>
        </select>
    </div>
    <button id="process-payment-btn" type="submit">Process Payment</button>
</g:form>

</body>
</html>