import Header from "./layout/Header";
import "./resource/App.scss";
import { useState } from "react";
import { useNavigate } from 'react-router-dom';
import MovieModel from "./model/MovieModel";


const owlClass = "App";

function SharePage() {
  const navigate = useNavigate();
  const [link, setLink] = useState<string>()
  const [errorMsg, setErrorMsg] = useState<string>()

  const _share = () => {
    if (!link) {
      setErrorMsg('Please input link')
      return;
    }
    setErrorMsg(null as unknown as string)
    MovieModel.share(link)
      .then(resp => {
        if (resp.error == 0) {
          navigate("/")
        } else {
          setErrorMsg(resp.msg)
        }
      })
  }

  return (
    <div>
      <Header />
      <div className={`${owlClass}__content`}>
        <div className={`${owlClass}__content__share-form`}>
          <h5 className={`${owlClass}__content__share-form__title`}>Share a youtube movie</h5>
          <div className={`${owlClass}__content__share-form__input`}>
            <label style={{ paddingRight: '20px' }}>Youtube URL</label>
            <input onChange={(e: React.FormEvent<HTMLInputElement>) => {
              setLink(e.currentTarget.value)
            }} />
          </div>
          {errorMsg && <p style={{ color: 'red', padding: '10px' }}>{errorMsg}</p>}
          <button onClick={_share} style={{ marginTop: '10px' }}>Share</button>
        </div>
      </div>
    </div>
  );
}

export default SharePage;
