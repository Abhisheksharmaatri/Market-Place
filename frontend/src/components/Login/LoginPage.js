import {
    useState
} from 'react';

import {user, backend} from "../../public/config.js"
import '../../public/login.css';

import SERVICE_URLS from "../../api/config";


function LoginPage(props) {
    const [email, setEmail] = useState('')
    const [password, setPassword] = useState('')
    const [message, setMessage] = useState('')
    const [emailError, setEmailError] = useState('Email address should be properly formatted.')
    const [passwordError, setPasswordError] = useState('Password must be at least '+user.password.length+' characters long.')

    const checkEmail = () => {
        const emailValid = /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)
        if (!emailValid) {
            setEmailError('Invalid email address.')
            setMessage('Email Incorrect')
        } else {
            setEmailError('')
        }
    }

    const checkPassword = () => {
        if (password.length < user.password.length) {
            setPasswordError('Password must be at least 8 characters long.')
            setMessage('Password Incorrect')
        } else {
            setPasswordError('')
        }
    }

    const handleLogin = (e) => {
        e.preventDefault()
        checkEmail()
        checkPassword()

        if (emailError === '' && passwordError === '') {
            const requestBody = {
                mail: email,
                password: password
            }
//            const url = backend.url + '/user/login'
            fetch(
//            url,
`${SERVICE_URLS.url}/user/login`,
            {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(requestBody)
                })
                .then(response => {
                    console.log(response)
                    return response.json()
                })
                .then(data => {
                    if (!data.token) {
                        throw new Error("Login failed");
                    }

                    localStorage.setItem("token", data.token);
                    window.location.href = "/home";
                })
                .catch(error => {
                    setMessage('An error occurred, please try again.')
                })
        }
    }

    const updateEmail = (e) => { 
        setEmail(e.target.value)
        setMessage('')
    }

    const updatePassword = (e) => {
        setPassword(e.target.value)
        setMessage('')
    }

    return (
        <div className="login__container">
            <h1>Login</h1>
            <form onSubmit={handleLogin} className='login__form'>
                <div className="form__group">
                    <label htmlFor="email">Email address</label>
                    <input type="text" id="email" className="form-control" value={email} onChange={e => updateEmail(e)} onBlur={checkEmail} />
                    <small className="text-danger">{emailError}</small>
                </div>
                <div className="form__group">
                    <label htmlFor="password">Password</label>
                    <input type="text" id="password" className="form-control" value={password} onChange={e => updatePassword(e)} onBlur={checkPassword} />
                    <small className="text-danger">{passwordError}</small>
                </div>
                <div className='form__message'>
                    <p className="message__danger">{message}</p>
                </div>
                <div className="form__actions">
                    <button type="submit" className="btn btn-primary">Login</button>
                    <button className="btn btn-primary" onClick={()=>{window.location.href='/signup'}}>To Sign Up Page</button>
                </div>
            </form>
        </div>
    )
}

export default LoginPage;