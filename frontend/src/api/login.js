async function login(email, password) {
  const res = await fetch("https://market-place-api-3bbo.onrender.com/api/user/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      mail: email,
      password: password
    })
  });

  if (!res.ok) {
    throw new Error("Login failed");
  }

  const data = await res.json();

  // JWT from backend
  return data.token;
}
