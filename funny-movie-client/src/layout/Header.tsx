import { useEffect, useState } from "react";
import "../resource/App.scss";
import { UserForm } from '../interface/index'
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useTypedSelector } from '../hooks/useTypeSelector';
import { bindActionCreators } from 'redux'
import * as actionCreators from '../redux/actionCreators'
import UserModel from "../model/UserModel";

const owlClass = "App";
export default function Header() {

    const dispatch = useDispatch();
    const navigate = useNavigate();
    const { setUser, login } = bindActionCreators(actionCreators, dispatch);

    const { user } = useTypedSelector((state) => state.app);
    const [userForm, setUserForm] = useState<UserForm>({} as UserForm)
    const [errorMsg, setErrorMsg] = useState<string>()


    const _login = () => {
        if (!userForm || !userForm.email) {
            setErrorMsg('Please input email')
            return;
        }

        if (!userForm || !userForm.password) {
            setErrorMsg('Please input password')
            return;
        }
        setErrorMsg(null as unknown as string)
        UserModel.login(userForm)
            .then(resp => {
                if (resp.error == 0) {
                    setUser(resp.data)
                } else {
                    setErrorMsg(resp.msg)
                }
            })
    }


    const _register = () => {
        if (!userForm.email) {
            setErrorMsg('Please input email')
            return;
        }

        if (!userForm.password) {
            setErrorMsg('Please input password')
            return;
        }

        setErrorMsg(null as unknown as string)
        UserModel.register(userForm)
            .then(resp => {
                if (resp.error == 0) {
                    alert("Successfull registed")
                } else {
                    setErrorMsg(resp.msg)
                }
            })
    }


    const isLogin = () => {
        setErrorMsg(null as unknown as string)
        UserModel.get()
            .then(resp => {
                if (resp.error == 0) {
                    setUser(resp.data)
                }
            })
    }

    useEffect(() => {
        isLogin()
    }, [])


    return (
        <div className={`${owlClass}__header`}>
            <a href="#" className={`${owlClass}__header__logo`}>FUNNY MOVIE</a>
            <div className={`${owlClass}__header__header-right`}>
                {!user &&
                    <>
                        <form>
                            <input onChange={(e: React.FormEvent<HTMLInputElement>) => {
                                setUserForm({ ...userForm, email: e.currentTarget.value })
                            }} type="text" placeholder="email" />

                            <input onChange={(e: React.FormEvent<HTMLInputElement>) => {
                                setUserForm({ ...userForm, password: e.currentTarget.value })
                            }} type="password" placeholder="password" />
                            <a href="#" onClick={_register}>Register</a>
                            <a href="#" onClick={_login}>Login</a>
                            {errorMsg &&
                                <p className="error">{errorMsg}</p>
                            }
                        </form>

                    </>
                }
                {user &&
                    <>
                        <p>Welcome: trungnd@gmail.com</p>
                        <button onClick={() => { navigate("/share") }}>Share a movie</button>
                        <button>Logout</button>
                    </>
                }
            </div>
        </div>
    )
} 