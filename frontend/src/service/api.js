import axios from 'axios';

// API export
export default {
    outcode: {
        getOutcode: id => axios.get(`/api/outcode/getOutcode/${id}`),
        getAllOutcodes: () => axios.get(`/api/outcode/getAllOutcodes`),
        updateOutcode: outcode => axios.put(`/api/outcode/updateOutcode`, outcode),
        deleteOutcode: id => axios.delete(`/api/outcode/deleteOutcode/${id}`),
        calculateDistance: (firstOutcode, secondOutcode) => axios.get(`/api/outcode/calculateDistance/${firstOutcode}/${secondOutcode}`),
    },
    fullPostcode: {
        getFullPostcode: postcode => axios.get(`/api/full-postcode/getFullPostcode/${postcode}`),
        updateFullPostcode: fullPostcode => axios.put(`/api/full-postcode/updateFullPostcode`, fullPostcode),
        deleteFullPostcode: id => axios.delete(`/api/full-postcode/deleteFullPostcode/${id}`),
        calculateDistance: (firstFullPostcode, secondFullPostcode) => axios.get(`/api/full-postcode/calculateDistance/${firstFullPostcode}/${secondFullPostcode}`),
        calculateJsonDocument: (firstFullPostcode, secondFullPostcode) => axios.get(`/api/full-postcode/calculateJsonDocument/${firstFullPostcode}/${secondFullPostcode}`),
    },
};