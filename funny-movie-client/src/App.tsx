import Header from "./layout/Header";
import "./resource/App.scss";
import { useEffect } from "react";
import { useDispatch } from 'react-redux';
import { useTypedSelector } from './hooks/useTypeSelector';
import { bindActionCreators } from 'redux'
import * as actionCreators from './redux/actionCreators'
import MovieModel from "./model/MovieModel";

const owlClass = "App";

function App() {
  const dispatch = useDispatch();
  const { moviePage } = useTypedSelector((state) => state.app);
  const { setMoviePage } = bindActionCreators(actionCreators, dispatch);

  useEffect(() => {
    MovieModel.find(0, 1000)
      .then(resp => {
        if (resp.error == 0) {
          setMoviePage(resp.data)
        }
      })
  }, [])

  return (
    <div>
      <Header />
      <div className={`${owlClass}__content`}>
        {moviePage?.list && moviePage.list.map((movie, index) =>
          <div key={index} className={`${owlClass}__content__post`}>
            <div className={`${owlClass}__content__post__column__player`}>
              <iframe width="100%" height="100%" src={movie.url}></iframe>
            </div>
            <div className={`${owlClass}__content__post__column__info`}>
              <h2>{movie.title}</h2>
              <h5> <span>Shared by: </span>{movie.user.email}</h5>
              <span className="v2">Description: </span>
              <p>{movie.desc}</p>
            </div>
          </div>
        )}
      </div>
    </div>
  );
}

export default App;
