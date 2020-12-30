let customersUrl = "http://localhost:8080/v1/customers";
let ordersUrl = "http://localhost:8081/v1/orders";
let paymentsUrl = "http://localhost:8082/v1/transactions";

let app = new Vue({
    el: '#app',
    data: {
        customers: [],
        orders: [],
        transactions: []
    }
  });

fetch(customersUrl)
    .then(r => r.json())
    .then(data => {
        app.customers = data;
    });

fetch(ordersUrl)
    .then(r => r.json())
    .then(data => {
        app.orders = data;
    });

fetch(paymentsUrl)
    .then(r => r.json())
    .then(data => {
        app.transactions = data;
    });