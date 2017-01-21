import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import SlotCat from '../components/SlotCat';

class SlotLayoutContainer extends Component {

  constructor(props) {
    super(props);
    this.images = sliders;
  }

  render() {
    return (
      <div className="page home-page">
        <TopBanner {...this.props} />
        <HeaderNav />
        <ImageSlider images={this.images}/>
        <SlotCat {...this.props}/>

        
        {this.props.children}

      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages} = state;
  return {user, messages};
}

export default connect(mapStateToProps)(SlotLayoutContainer);