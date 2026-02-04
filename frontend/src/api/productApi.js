
import axios from "axios";
import SERVICE_URLS from "./config";

export const getAllProducts = async () => {
  const response = await axios.get(
    `${SERVICE_URLS.product}/product`
  );
  return response.data;
};


export const createProduct = async (product) => {
  const response = await axios.post(
    `${SERVICE_URLS.product}/product`,
    product
  );
  return response.data;
};


export const updateProduct = async (product, id) => {
  const response = await axios.put(
    `${SERVICE_URLS.product}/product`,
    {
      name: product.name,
      description: product.description
    },
    {
      params: { id },
      headers: {
        "Content-Type": "application/json" // IMPORTANT
      }
    }
  );
  return response.data;
};





export const deleteProduct=async(id)=>{
  const response=await axios.delete(
    `${SERVICE_URLS.product}/product`,
    {
      params:{id}
    }
  );
  return response.data
}


export const getOneProduct=async(id)=>{
  const response=await axios.get(
    `${SERVICE_URLS.product}/product/item`,
    {
      params:{id}
    }
  )
  return response.data;
}
