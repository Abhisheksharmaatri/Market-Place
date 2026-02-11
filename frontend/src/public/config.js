module.exports = {
    backend: {
        url:"https://market-place-user.onrender.com/api"
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