import React, {Component, PropTypes} from 'react';
import Slider from 'react-slick';
import {resourceUrl, absUrl} from '../utils/url';

class ImageSlider extends Component {
  render() {
    const settings = {
      dots: false,
      infinite: true,
      speed: 500,
      slidesToShow: 1,
      slidesToScroll: 1,
      arrows: false,
    };

    return (
      <div className="home-slider">
        <Slider {...settings}>
          {this.props.images.map((image, index) => <div key={index}><img src={absUrl(image)} /></div>)}
        </Slider>
      </div>
    );
  }
}

ImageSlider.propTypes = {
  images: PropTypes.array.isRequired
};

export default ImageSlider;