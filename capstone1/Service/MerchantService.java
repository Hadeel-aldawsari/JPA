package com.example.capstone1.Service;

import com.example.capstone1.Model.Merchant;
import com.example.capstone1.Repository.MerchantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MerchantService {

    private final MerchantRepository merchantRepository;

    public List<Merchant> getAll() {
        return merchantRepository.findAll();
    }

    public void add(Merchant merchant) {
        merchantRepository.save(merchant);
    }

    public boolean update(Integer id, Merchant updatedMerchant) {
        Merchant existingMerchant = merchantRepository.getById(id);
        if (existingMerchant != null) {
            existingMerchant.setName(updatedMerchant.getName());
            merchantRepository.save(existingMerchant);
            return true;
        }
        return false;
    }

    public boolean delete(Integer id) {
        Merchant existingMerchant = merchantRepository.getById(id);
        if (existingMerchant != null) {
            merchantRepository.delete(existingMerchant);
            return true;
        }
        return false;
    }

    public boolean isMerchantsExist(Integer merchantId) {
        return merchantRepository.existsById(merchantId);
    }
}
