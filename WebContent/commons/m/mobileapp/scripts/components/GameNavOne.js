import React, {Component, PropTypes} from 'react';
import {absUrl} from '../utils/url';
import {Link} from 'react-router';

class GameNavsOne extends Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.gameNavs_one = [{
            navKind: '真人视讯',
            navEng: 'casino',
            navKindStyle:'navKind_one',
            navEngStyle:'navEng_one',
            gameNavs_oneBg:'bgOne',
            to:'/live'
        }, {
            navKind: '体育赛事',
            navEng:'Sporting',
            navKindStyle:'navKind_two',
            navEngStyle:'navEng_two',
            gameNavs_oneBg:'bgTwo',
            to:'/sport'
        },{
            navKind: '电子游戏',
            navEng: 'Electronic',
            navKindStyle:'navKind_one',
            navEngStyle:'navEng_one',
            gameNavs_oneBg:'bgOne',
            to:'/slot'
        }, {
            navKind: '彩票游戏',
            navEng:'Lottery ',
            navKindStyle:'navKind_two',
            navEngStyle:'navEng_two',
            gameNavs_oneBg:'bgTwo',
            to:absUrl('/m/wap')
        }];
    }
    render() {
      return (
        <ul className="main-content">{
          this.gameNavs_one.map((nav,index)=>{
            if(nav.navKind!='彩票游戏'){
                return <li key={index} className="game-navs-item gameNavs_one-item" >
                  <div className="gameNavBorder">
                    <Link to={nav.to}>
                        <i className={nav.gameNavs_oneBg}></i>
                        <i className={nav.navKindStyle}>{nav.navKind}</i>
                        <i className={nav.navEngStyle}>{nav.navEng}</i>
                    </Link>
                  </div>
                </li>
            }else{
                return <li key={index} className="game-navs-item gameNavs_one-item" >
                  <div className="gameNavBorder">
                    <a href={nav.to}>
                        <i className={nav.gameNavs_oneBg}></i>
                        <i className={nav.navKindStyle}>{nav.navKind}</i>
                        <i className={nav.navEngStyle}>{nav.navEng}</i>
                    </a>
                  </div>
                </li>
            }
          })
        }
        </ul>
      );
    }
}

export default GameNavsOne;