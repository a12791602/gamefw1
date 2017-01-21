import React, {PropTypes, Component} from 'react';
import {Link} from 'react-router';
import InfiniteScroll from 'react-infinite-scroller';

import {loadSlotGameItems, checkFlatStatus, likeSlot} from '../actions/SlotAction';
import {resourceUrl, slotImgUrl, slotRedirect, redirect} from '../utils/url';
import {userIsLogin} from '../utils/user';

class SlotGameItems extends Component {

  constructor(props) {
    super(props);
    this.searchGame = this.searchGame.bind(this);
    this.loadMore = this.loadMore.bind(this);
    this.params = this.props.params;
    this.timeout = null;
    this.crtSlot = null;
    this.keyword = this;
    this.pagin = {
      from: 0,
      size: 6,
      page: 1,
    };
    this.state = {
      items: [],
      hasMore: true
    };
    this.totalItems= [];
  }

  nextPaginItems() {
    let page = this.pagin.page;
    let from = (page + 1) * this.pagin.size;
    const totalItems = this.totalItems;

    if (this.totalItems.length <= 0) return [];

    let pageItems = totalItems.slice(0, from + this.pagin.size);
    if (pageItems.length > 0 ) {
      this.pagin.page += 1;
    }
    return pageItems;
  }

  nextPaginReset() {
    this.pagin = {
      from: 0,
      size: 6,
      page: 1,
    };
  }

  loadSlotItems(name, type, keyword="") {
    const {dispatch} = this.props;
    dispatch(loadSlotGameItems(name, type, keyword));
  }

  componentWillReceiveProps(newProps) {

    const {name, type} = this.params;
    if (name != newProps.params.name 
      || type != newProps.params.type) {
      this.loadSlotItems(newProps.params.name, newProps.params.type);
    }

    const {slot} = newProps;
    for (let flatName in slot.flatStatus) {
      if (slot.flatStatus[flatName].status) {
        let redirectUri = null;
        switch (flatName) {
          case 'mg':
            redirectUri = slotRedirect(flatName, {
              gameName: this.crtSlot.eleGameId,
              gameCode: this.crtSlot.remark,
            });
            break;
          case 'pt':
            redirectUri = slotRedirect(flatName, {
              gameName: this.crtSlot.eleGameCode
            });
            break;
          case 'os':
            redirectUri = slotRedirect(flatName, {
              gameName: this.crtSlot.eleGameId
            });
            break;
          case 'ttg':
            redirectUri = slotRedirect(flatName, {
              gameId: this.crtSlot.id,
              gameName: this.crtSlot.eleGameEnname,
              eleGameType: this.crtSlot.eleGameType2
            });
            break;
          case 'nt':
            redirectUri = slotRedirect(flatName, {
              game: this.crtSlot.eleGameCode,
            });
            break;
        }
        return redirect(redirectUri, false);
      }
      else if (slot.flatStatus[flatName].status == false 
        && slot.flatStatus[flatName].res.msg) {
        alert(slot.flatStatus[flatName].res.msg);
      }
    }

    this.params = newProps.params;

    let newtype = this.params.type;
    this.totalItems = typeof newProps.slot[newtype] == 'undefined' ? []: newProps.slot[newtype];
    this.nextPaginReset();
    this.loadMore();
  }

  componentDidMount() {
    const {name, type} = this.context.router.params;
    this.loadSlotItems(name, type);
  }

  searchGame() {
    let keyword = this.refs.keyword.value;
    this.keyword = keyword;
    const {name, type} = this.context.router.params;
    if (this.timeout != null) {
      clearTimeout(this.timeout);
    }
    this.timeout = setTimeout(() => {
      this.loadSlotItems(name, type, keyword);
    }, 500);
  }

  handleSlotPlay(slot) {
    const {user, dispatch} = this.props;
    const {name, type} = this.context.router.params;
    if (userIsLogin(user)) {
      this.crtSlot = slot;
      dispatch(checkFlatStatus(name));
    }
    else {
      alert('请先登录');
    }
  }

  handleLikeClick(slot) {
    const {name, type} = this.context.router.params;
    const {dispatch} = this.props;
    const {user} =this.props;
    slot.isFavourite = !slot.isFavourite;
    if (userIsLogin(user)) {
      let keyword = this.refs.keyword.value;
      dispatch(likeSlot(name, slot, type, keyword));
      this.render();
    }
    else {
      alert('请先登录');
    }
  }

  iLikeit(slot) {
    return slot.isFavourite;
  }

  loadMore(page) {
    let state = this.state;
    state['items'] = this.nextPaginItems();

    if (state.items.length >= this.totalItems.length ) {
      state['hasMore'] = false;
    }
    else {
      state['hasMore'] = true;
    }
    this.setState(state);
  }

  render() {
    const {name, type} = this.context.router.params;
    let items = this.state.items;
    if (items.length <= 0 && this.keyword <= 0) {
      return (
        <div className="no-slot-items">
          <p>该分类下暂无游戏</p>
        </div>
      );
    }

    return (
      <div className={ "slot-game-items" + " slot-game-items-" + name}>
        <div className="search-box clearfix">
          <i className={"slot-logo slot-logo-" + name}></i>
          <input type="text" placeholder="游戏搜索" ref="keyword" onChange={this.searchGame} />
        </div>
        <InfiniteScroll pageStart={0} initialLoad={false} loadMore={this.loadMore} hasMore={this.state.hasMore} loader={ <div className="loader-msg">加载中...</div> }>
          <div className="items clearfix">
            {items.map( (item, index) => {
              return (<div key={index} className="image-card slot-game" >
                <div className="img" onClick={ () => this.handleSlotPlay(item) }>
                  <i style={ {backgroundImage: "url("+slotImgUrl(name, item.eleGamePic)+")" } }></i>
                </div>
                <div className="name">
                  <h3 onClick={ () => this.handleSlotPlay(item) }>{item.eleGameCnname}</h3>
                  <i onClick={ () => this.handleLikeClick(item) } className={'icon ' + ( this.iLikeit(item) ? 'icon-like-on': 'icon-like-off') }></i>
                </div>
              </div>);
            })}
          </div>
        </InfiniteScroll>
      </div>
    );
  }
};

SlotGameItems.contextTypes = {
  router: PropTypes.object
};

SlotGameItems.propTypes = {
  slot: PropTypes.object.isRequired,
  user: PropTypes.object.isRequired
};

export default SlotGameItems;