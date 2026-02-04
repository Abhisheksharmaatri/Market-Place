import axios from "axios";
import SERVICE_URLS from "./config";

export const getOrders = async () => {
  const response=await axios.get(
    `${SERVICE_URLS.order}/order`
  )
  return response.data;
};


export const createOrder=async (order) =>{
  const response=await axios.post(
    `${SERVICE_URLS.order}/order`,
    order
  );
  return response.data;
}


// export const updateOrder=async(order, id)=>{
//   const response=await axios.put(
//     `${SERVICE_URLS.order}/order`,
//     {

//     }
//   )
//   return response;
// }

export const deleteOrder=async(id)=>{
  const numId=Number(id)
  const response=await axios.delete(
    `${SERVICE_URLS.order}/order`,
    {
      params:{
      id:numId
    }
    }
  );
  return response.data;
}


export const getOneOrder = async(id)=>{
  const response=await axios.get(
    `${SERVICE_URLS.order}/order/item`,
  {
    params:{
      id:Number(id)
    }
  }
  );
  return response.data;
}