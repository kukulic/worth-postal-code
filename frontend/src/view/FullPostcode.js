import React, {Component} from 'react';
import {Row, Col, Container, Button, Form} from 'react-bootstrap';
import 'react-dropdown/style.css'
import '../css/main.css';
import {api} from '../service';

export class FullPostcode extends Component {
    state = {
        fullPostcode: undefined,
        postcode: undefined,
        fullPostcodeFirst: undefined,
        fullPostcodeSecond: undefined,
        distance: undefined,
        loading: true,
        updating: false,
        form: undefined,
        test: undefined
    };

    componentDidMount() {
    }

    getPostcode = async () => {
        try {
            this.setState({
                fullPostcode: (await api.fullPostcode.getFullPostcode(this.state.postcode)).data
            });
        } catch (e) {
            this.setState({error: 'Error happened while updating postal office'});
        } finally {
        }
    };

    updatePostOffice = async (event) => {
        event.preventDefault();
        const {fullPostcode} = this.state
        try {
            this.setState({
                test: (await api.fullPostcode.updateFullPostcode(fullPostcode)).data
            });
        } catch (e) {
            this.setState({error: 'Error happened while updating postal office'});
        } finally {
            this.setState({updating: false, postcode: '', fullPostcode: undefined});
        }
    };

    deletePostOffice = async (id) => {
        try {
            await api.fullPostcode.deleteFullPostcode(id);
            this.setState({fullPostcode: undefined});
        } catch (e) {
            this.setState({error: 'Unexpected error'});
        } finally {
            this.setState({loading: false, updating: false, postcode: ''});
        }
    };

    calculateDistance = async (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        let fullPostcodeFirst = data.get('firstPostcode');
        let fullPostcodeSecond = data.get('secondPostcode');

        if (fullPostcodeFirst !== undefined && fullPostcodeSecond !== undefined) {
            try {
                this.setState({
                    distance: (await api.fullPostcode.calculateDistance(fullPostcodeFirst, fullPostcodeSecond)).data
                });
            } catch (e) {
                this.setState({error: 'Data not available'});
            } finally {
                api.fullPostcode.calculateJsonDocument(fullPostcodeFirst, fullPostcodeSecond);
            }
        }
    };

    postcodeInputHandler = event => {
        this.setState({
            postcode: event.target.value
        });
    };

    handlePostcodeChange = (event) => {
        let fullPostcode = Object.assign({}, this.state.fullPostcode);
        fullPostcode.postcode = event.target.value;
        this.setState({fullPostcode: fullPostcode});
    };

    handleLatitudeChange = (event) => {
        let fullPostcode = Object.assign({}, this.state.fullPostcode);
        fullPostcode.latitude = event.target.value;
        this.setState({fullPostcode: fullPostcode});
    };

    handleLongitudeChange = (event) => {
        let fullPostcode = Object.assign({}, this.state.fullPostcode);
        fullPostcode.longitude = event.target.value;
        this.setState({fullPostcode: fullPostcode});
    };

    openUpdateForm() {
        this.setState({updating: true});
    };

    back() {
        this.setState({updating: false});
    };


    render() {
        const {
            fullPostcode,
            distance,
            updating
        } = this.state;


        return (
            <Container className="container-small space-top-large">
                <div className="header-title-medium">
                    <span>Full postal code</span>
                </div>

                <form className="space-top-large">
                    <input type="text" name="postcodeInput" value={this.state.postcode}
                           onChange={this.postcodeInputHandler}/>
                </form>

                <Button className="btn-space space-top-small" variant="primary"
                        onClick={() => this.getPostcode()}>Find postcode details</Button>

                {fullPostcode ?
                    <div className="space-top-small">
                        {updating ?
                            <div>
                                <Form onSubmit={this.updatePostOffice}>
                                    <Form.Group as={Row} controlId="formFullPostcode">
                                        <Form.Label column sm={4}>Postal code</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control onChange={this.handlePostcodeChange}
                                                          value={fullPostcode.postcode}/>
                                        </Col>
                                    </Form.Group>
                                    <Form.Group as={Row} controlId="formLatitude">
                                        <Form.Label column sm={4}>Latitude</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control type="number" onChange={this.handleLatitudeChange}
                                                          value={fullPostcode.latitude}/>
                                        </Col>
                                    </Form.Group>
                                    <Form.Group as={Row} controlId="formLongitude">
                                        <Form.Label column sm={4}>Longitude</Form.Label>
                                        <Col sm={8}>
                                            <Form.Control type="number" onChange={this.handleLongitudeChange}
                                                          value={fullPostcode.longitude}/>
                                        </Col>
                                    </Form.Group>
                                    <Button className="btn-space" variant="primary" type="submit">Update</Button>
                                    <Button className="btn-space" variant="secondary"
                                            onClick={() => this.back()}>Back</Button>
                                </Form>
                            </div> :
                            <div>
                                <div>
                                    <Row>
                                        <Col md="4">Postal name:</Col>
                                        <Col>{fullPostcode.postcode}</Col>
                                    </Row>
                                    <Row>
                                        <Col md="4">Latitude:</Col>
                                        <Col>{fullPostcode.latitude}</Col>
                                    </Row>
                                    <Row>
                                        <Col md="4">Longitude:</Col>
                                        <Col>{fullPostcode.longitude}</Col>
                                    </Row>
                                </div>
                                <Button className="space-top-small btn-space" variant="primary"
                                        onClick={() => this.openUpdateForm()}>
                                    Update post office
                                </Button>
                                <Button className="space-top-small btn-space" variant="danger"
                                        onClick={() => this.deletePostOffice(fullPostcode.id)}>
                                    Delete post office
                                </Button>
                            </div>
                        }
                    </div>
                    :
                    <div className="space-top-small">
                        Need to enter valid postcode to change data
                    </div>
                }
                <div className="space-top-large">
                    <Form onSubmit={this.calculateDistance}>
                        <Row>
                            <Col>
                                <Form.Group controlId="formFirstPostcode">
                                    <Form.Control type="text" name="firstPostcode" placeholder="Select first postcode"/>
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="formSecondPostcode">
                                    <Form.Control type="text" name="secondPostcode"
                                                  placeholder="Select second postcode"/>
                                </Form.Group>
                            </Col>
                        </Row>
                        <Row className="btn-calculate">
                            <Button variant="primary" type="submit">Calculate distance</Button>
                        </Row>
                    </Form>
                </div>
                {(distance != null) &&
                <div className="space-top-small">
                    {distance}
                </div>
                }
            </Container>
        );
    }
}
