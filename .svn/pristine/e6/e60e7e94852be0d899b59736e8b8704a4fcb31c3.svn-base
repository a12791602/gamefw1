import React, {Component, PropTypes} from 'react';
import {connect} from 'react-redux';

import TopBanner from '../components/TopBanner';
import HeaderNav from '../components/HeaderNav';
import ImageSlider from '../components/ImageSlider';
import Messages from '../components/Messages';
import SportTypes from '../components/SportTypes';
import Footer from '../components/Footer';

class SportContainer extends Component {

  constructor(props) {
    super(props);
    this.images = sliders;
  }

  render() {
    return (
      <div className="page home-page">
        <TopBanner {...this.props} />
        <HeaderNav />
        <ImageSlider images={this.images} />
        <Messages {...this.props}/>

        <SportTypes {...this.props}/>

        <Footer />
      </div>
    );
  }
}

function mapStateToProps(state) {
  const {user, messages} = state;
  return {user, messages};
}

export default connect(mapStateToProps)(SportContainer);