import React, {Component, PropTypes} from 'react';
import {userIsLogin} from '../utils/user';
import {redirect, absUrl} from '../utils/url';

class MoneyNav extends Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.moneyNavs = [{
            navKind: '充值',
            className: 'mNavImg_one',
            textName:'moneyNavText_one money-nav'
        }, {
            navKind: '提现',
            className: 'mNavImg_two',
            textName:'moneyNavText_two money-nav'
        }, {
            navKind: '额度转换',
            className: 'mNavImg_three',
            textName:'moneyNavText_three money-nav'
        }];
    }

    handleLinkTo(nav) {
        const {user} = this.props;
        const {router} = this.context;
        if (userIsLogin(user)) {
            redirect('/m/wap/member#/home');
        }
        else {
            router.push('/login');
        }
    }

    render() {
      return (
      	<ul className="main-content">{
    		  this.moneyNavs.map((nav,index)=>{
    			  return <li key={index} className="moneyNav-item" onClick={() => this.handleLinkTo(nav) }>
              <i className={nav.className}></i>
              <i className={nav.textName}>{nav.navKind}</i>
            </li>
    		  })
        }
        </ul>
      );
    }
}

MoneyNav.propTypes = {
    user: PropTypes.object.isRequired
};
MoneyNav.contextTypes = {
    router: PropTypes.object.isRequired
};

export default MoneyNav;