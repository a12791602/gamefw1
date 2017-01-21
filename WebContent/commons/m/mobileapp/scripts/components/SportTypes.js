import React, {PropTypes, Component} from 'react';

import {Link} from 'react-router';
import {redirect} from '../utils/url';
import {userIsLogin} from '../utils/user';

class SportTypes extends Component {

  constructor(props) {
    super(props);
    this.sports = [ {
      id: 'sb',
      title: '沙巴体育',
      url: '/m/game/forwardGame?&gameType=sb',
    }, {
      id: 'huangguan',
      title: '皇冠体育',
      url: '/m/sport/list',
    }];
  }

  handleClick(sport) {
    const {user} = this.props;    
    if (!userIsLogin(user) && sport.id == 'sb') {
      alert('请先登录');
    }
    redirect(sport.url);
  }

  render() {
    return (
      <div className="slot-type-links sport-type-links">
        <ul className="clearfix items">
          {this.sports.map((sport, index) => {
            return (
              <li key={index} className={"slot-item-" + sport.id}  onClick={() => this.handleClick(sport)} >
                <div className="slot-link-inner"><a href="javascript:void(0);"> <span className="slot-title">{sport.title}</span> <br /> </a></div>
              </li>);
          })}
        </ul>
      </div>
    );
  }
};

SportTypes.propTypes = {
  user: PropTypes.object.isRequired
};

export default SportTypes;