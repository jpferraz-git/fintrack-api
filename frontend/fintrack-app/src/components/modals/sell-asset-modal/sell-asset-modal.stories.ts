import type { Meta, StoryObj } from '@storybook/angular';
import { SellAssetModal } from './sell-asset-modal.component';

const meta: Meta<SellAssetModal> = {
  title: 'Components/SellAssetModal',
  component: SellAssetModal,
  tags: ['autodocs'],
};

export default meta;
type Story = StoryObj<SellAssetModal>;

export const Primary: Story = {
  args: {},
};
