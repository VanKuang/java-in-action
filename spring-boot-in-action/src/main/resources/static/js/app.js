window.onload = function () {
    var app = new Vue({
        el: "#app",
        data: {
            message: "Hello world!",
            title: "Now is " + new Date()
        }
    });

    var app1 = new Vue({
        el: "#app1",
        data: {
            visible: true
        }
    });

    var app2 = new Vue({
        el: "#app2",
        data: {
            elements: [
                {name: "Kobe"},
                {name: "James"},
                {name: "Curry"}
            ]
        }
    });

    var app3 = new Vue({
        el: "#app3",
        data: {
            message: "Hello world"
        },
        methods: {
            reverse: function () {
                this.message = this.message.split("").reverse().join("")
            }
        }
    });

    var app4 = new Vue({
        el: "#app4",
        data: {
            message: "Hello world"
        }
    });

    Vue.component("players", {
        props: ["player"],
        template: "<li>{{ player.name }}</li>"
    });

    var app5 = new Vue({
        el: "#app5",
        data: {
            players: [
                {name: "KB"},
                {name: "LBJ"},
                {name: "SC"}
            ]
        }
    });
};