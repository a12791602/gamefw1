import React, {PropTypes, Component} from 'react';

import {Link} from 'react-router';
import {userIsLogin} from '../utils/user';

class SlotCat extends Component {

  constructor(props) {
    super(props);
    this.cats = [{
      title: '全部游戏',
      cat: 'all'
    }, {
      title: '热门游戏',
      cat: 'hot'
    }, {
      title: '最新游戏',
      cat: 'new',
    }, {
      title: '我的收藏',
      cat: 'favourite',
    }];

  }

  handleLinkClick(cat) {
    const {user} = this.props;
    const {router} = this.context;
    const {name, type} = router.params;
    if (cat.cat == 'favourite' && !userIsLogin(user) ) {
      alert('请先登录');
    }
    else {
      let link = `/slot/${name}/${cat.cat}`;
      router.push(link);
    }
  }

  render() {
    const {router} = this.context;
    const {name, type} = router.params;
    return (
      <div className="slot-cat-links">
        <ul className="items clearfix">
          {this.cats.map( (cat, index) => {
            return <li className={(cat.cat == type ) ? 'active': ''} key={index}><a href="javascript:void(0);" onClick={ () => this.handleLinkClick(cat)}  >{cat.title}</a></li>;
          })}
        </ul>
      </div>
    );
  }
};

SlotCat.contextTypes = {
  router: PropTypes.object
};

SlotCat.propTypes = {
  user: PropTypes.object.isRequired
};

export default SlotCat;