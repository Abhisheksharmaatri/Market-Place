module.exports = {
    backend: {
        url:"http://localhost:8080/api"
    },
    user: {
        name: {
            length: 5
        },
        password: {
            saltRounds: 10,
            length: 8
        }
    },
    room: {
        name: {
            length: 5
        },
        description: {
            length: 5
        }
    },
    port: 3000
}