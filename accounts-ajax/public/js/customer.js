/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


"use strict";

let module = angular.module('CustomerModule', ['ngResource']);



module.factory('customerJetty', function ($resource) {
    return $resource('http://localhost:9000/register', null, {update: {method: 'POST'}});
});

module.controller('CustomerController', function (customerJetty) {
    this.createCustomer = function (customer) {
        customerJetty.save({}, customer, function () {})
    };
});