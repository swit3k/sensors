db.createUser({
    user: 'sensor',
    pwd: 'sensorsAreCool',
    roles: [
        {
            role: "readWrite",
            db: "sensors"
        }
    ]
});
