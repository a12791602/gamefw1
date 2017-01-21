import React, {Component, PropTypes} from 'react';
import {absUrl} from '../utils/url';
import {Link} from 'react-router';

class GameNavsTwo extends Component {
    constructor(props) {
        super(props);
        this.state = {};
        this.gameNavs_two = [{
            navKind: '电子游戏',
            navEng: 'Electronic Game',
            navKindStyle:'navKind_one',
            navEngStyle:'navEng_one',
            gameNavs_oneBg:'bgOne',
            to:'/slot'
        }, {
            navKind: '彩票游戏',
            navEng:'Lottery Game',
            navKindStyle:'navKind_two',
            navEngStyle:'navEng_two',
            gameNavs_oneBg:'bgTwo',
            to:absUrl('/m/wap')
        }];
    }
    render() {
      return (
        <ul className="main-content">{
          this.gameNavs_two.map((nav,index)=>{
            if(index==0){
              return <li key={index} className="game-navs-item gameNavs_two-item" >
                <div className="gameNavBorder">  
                  <Link to={nav.to}>
                    <i className={nav.gameNavs_oneBg}></i>
                    <i className={nav.navKindStyle}>{nav.navKind}</i>
                    <i className={nav.navEngStyle}>{nav.navEng}</i>
                  </Link>
                </div>
                </li>
              }else{
                return <li key={index} className="game-navs-item gameNavs_two-item" >
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

export default GameNavsTwo;