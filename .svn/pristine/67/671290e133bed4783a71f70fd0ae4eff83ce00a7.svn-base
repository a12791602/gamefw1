import React, {PropTypes, Component} from 'react';

import {Link} from 'react-router';
import * as url from '../utils/url';
import {checkFlatStatus} from '../actions/LiveAction';

class LiveTypes extends Component {

  constructor(props) {
    super(props);
    this.lives = [{
      id: 'ag',
      title: 'AG视讯',
      url: ('/m/game/forwardGame?agGameType=2&gameType=ag')
    }, {
      id: 'ds',
      title: 'DS视讯',
      url: ('/m/game/forwardGame?gameType=ds')
    }, {
      id: 'sa',
      title: 'SA视讯',
      url: ('/m/game/forwardGame?gameType=sa')
    }, {
      id: 'hg',
      title: 'HG视讯',
      url: ('/m/game/forwardGame?gameType=hg')
    }, {
      id: 'og',
      title: 'OG视讯',
      url: ('/m/game/forwardGame?gameType=og')
    }, {
      id: 'null',
      title: '敬请期待',
    }];
    this.handleLiveClick = this.handleLiveClick.bind(this);
  }

  handleLiveClick(live) {
    const {dispatch} = this.props;
    if (live.id == 'null') {
      alert('游戏开发中, 敬请期待');
    }
    else {
      dispatch(checkFlatStatus(live.id));
    }
  }

  componentWillReceiveProps(newProps) {
    const {live} = newProps;
    for (let flat in live) {
      if (live[flat].status == true) {
        for (let index in this.lives) {
          if (this.lives[index].id == flat) {
            url.redirect(this.lives[index].url);
          }
        }
      } 
      else if (live[flat].status == false && live[flat].res.msg) {
        alert(live[flat].res.msg);
      }
    }
  }

  render() {
    return (
      <div className="slot-type-links live-type-links">
        <ul className="clearfix items">
          {this.lives.map((live, index) => {
            return (
              <li key={index} className={"slot-item-" + live.id}>
                <div className="slot-link-inner"><a href="javascript:void(0);" onClick={() => this.handleLiveClick(live) } className="liveLink"> {live.title}  </a></div>
              </li>);
          })}
        </ul>
      </div>
    );
  }
};

LiveTypes.propTypes = {
  live: PropTypes.object.isRequired  
};

export default LiveTypes;